package com.codered.demo;

import org.barghos.core.debug.Debug;
import org.barghos.math.Maths;
import org.barghos.math.matrix.Mat4;
import org.barghos.math.vector.quat.Quatf;
import org.barghos.math.vector.vec3.Vec3f;
import org.barghos.math.vector.vec4.Vec4f;

import com.codered.engine.EngineRegistry;
import com.codered.entities.Camera;
import com.codered.window.Window;
import com.codered.window.WindowContext;


/**
 * Represents the 3D cuboidal area of the world in which objects will cast
 * shadows (basically represents the orthographic projection area for the shadow
 * render pass). It is updated each frame to optimise the area, making it as
 * small as possible (to allow for optimal shadow map resolution) while not
 * being too small to avoid objects not having shadows when they should.
 * Everything inside the cuboidal area represented by this object will be
 * rendered to the shadow map in the shadow render pass. Everything outside the
 * area won't be.
 * 
 * @author Karl
 *
 */
public class ShadowBox {

	private static final float OFFSET = 10;
	private static final Vec3f UP = new Vec3f(0, 1, 0);
	private static final Vec3f FORWARD = new Vec3f(0, 0, -1);
	private static final float SHADOW_DISTANCE = 100;

	private float minX, maxX;
	private float minY, maxY;
	private float minZ, maxZ;
	private Mat4 lightViewMatrix;
	private Camera cam;

	private float fov;
	private float near_plane;
	
	private float farHeight, farWidth, nearHeight, nearWidth;

	/**
	 * Creates a new shadow box and calculates some initial values relating to
	 * the camera's view frustum, namely the width and height of the near plane
	 * and (possibly adjusted) far plane.
	 * 
	 * @param lightViewMatrix
	 *            - basically the "view matrix" of the light. Can be used to
	 *            transform a point from world space into "light" space (i.e.
	 *            changes a point's coordinates from being in relation to the
	 *            world's axis to being in terms of the light's local axis).
	 * @param camera
	 *            - the in-game camera.
	 */
	protected ShadowBox(Mat4 lightViewMatrix, Camera camera, float fov, float near_plane)
	{
		this.lightViewMatrix = lightViewMatrix;
		this.cam = camera;
		this.fov = fov;
		this.near_plane = near_plane;
		calculateWidthsAndHeights();
	}

	/**
	 * Updates the bounds of the shadow box based on the light direction and the
	 * camera's view frustum, to make sure that the box covers the smallest area
	 * possible while still ensuring that everything inside the camera's view
	 * (within a certain range) will cast shadows.
	 */
	protected void update()
	{
		Mat4 rotation = calculateCameraRotationMatrix();
		Vec3f forwardVector = rotation.transform(FORWARD, new Vec3f());

		Vec3f toFar = forwardVector.mulN(SHADOW_DISTANCE);
		Vec3f toNear = forwardVector.mulN(near_plane);
		
		Vec3f centerNear = toNear.addN(cam.getTotalPos());
		Vec3f centerFar  =toFar.addN(cam.getTotalPos());

		Vec4f[] points = calculateFrustumVertices(rotation, forwardVector, centerNear,
				centerFar);

		boolean first = true;
		for (Vec4f point : points) {
			if (first) {
				minX = point.getX();
				maxX = point.getX();
				minY = point.getY();
				maxY = point.getY();
				minZ = point.getZ();
				maxZ = point.getZ();
				first = false;
				continue;
			}
			if (point.getX() > maxX) {
				maxX = point.getX();
			} else if (point.getX() < minX) {
				minX = point.getX();
			}
			if (point.getY() > maxY) {
				maxY = point.getY();
			} else if (point.getY() < minY) {
				minY = point.getY();
			}
			if (point.getZ() > maxZ) {
				maxZ = point.getZ();
			} else if (point.getZ() < minZ) {
				minZ = point.getZ();
			}
		}
		maxZ += OFFSET;
	}

	/**
	 * Calculates the center of the "view cuboid" in light space first, and then
	 * converts this to world space using the inverse light's view matrix.
	 * 
	 * @return The center of the "view cuboid" in world space.
	 */
	protected Vec3f getCenter()
	{
		float x = (minX + maxX) / 2f;
		float y = (minY + maxY) / 2f;
		float z = (minZ + maxZ) / 2f;
		Vec4f cen = new Vec4f(x, y, z, 1);
		Mat4 invertedLight = lightViewMatrix.invertN();
		Vec4f r = invertedLight.transform(cen, new Vec4f());
		
		return new Vec3f(r.getX(), r.getY(), r.getZ());
	}

	/**
	 * @return The width of the "view cuboid" (orthographic projection area).
	 */
	protected float getWidth()
	{
		return maxX - minX;
	}

	/**
	 * @return The height of the "view cuboid" (orthographic projection area).
	 */
	protected float getHeight()
	{
		return maxY - minY;
	}

	/**
	 * @return The length of the "view cuboid" (orthographic projection area).
	 */
	protected float getLength()
	{
		return maxZ - minZ;
	}

	/**
	 * Calculates the position of the vertex at each corner of the view frustum
	 * in light space (8 vertices in total, so this returns 8 positions).
	 * 
	 * @param rotation
	 *            - camera's rotation.
	 * @param forwardVector
	 *            - the direction that the camera is aiming, and thus the
	 *            direction of the frustum.
	 * @param centerNear
	 *            - the center point of the frustum's near plane.
	 * @param centerFar
	 *            - the center point of the frustum's (possibly adjusted) far
	 *            plane.
	 * @return The positions of the vertices of the frustum in light space.
	 */
	private Vec4f[] calculateFrustumVertices(Mat4 rotation, Vec3f forwardVector, Vec3f centerNear, Vec3f centerFar)
	{
		Vec3f upVector = rotation.transform(UP, new Vec3f());
		Vec3f rightVector = forwardVector.cross(upVector, null);
		Vec3f downVector = upVector.invert(null);
		
		Vec3f leftVector = rightVector.invert(null);
		Vec3f farTop = centerFar.addN(upVector.mulN(farHeight));
		Vec3f farBottom = centerFar.addN(downVector.mulN(farHeight));
		Vec3f nearTop = centerNear.addN(upVector.mulN(nearHeight));
		Vec3f nearBottom = centerNear.addN(downVector.mulN(nearHeight));
		
		Vec4f[] points = new Vec4f[8];
		
		points[0] = calculateLightSpaceFrustumCorner(farTop, rightVector, farWidth);
		points[1] = calculateLightSpaceFrustumCorner(farTop, leftVector, farWidth);
		points[2] = calculateLightSpaceFrustumCorner(farBottom, rightVector, farWidth);
		points[3] = calculateLightSpaceFrustumCorner(farBottom, leftVector, farWidth);
		points[4] = calculateLightSpaceFrustumCorner(nearTop, rightVector, nearWidth);
		points[5] = calculateLightSpaceFrustumCorner(nearTop, leftVector, nearWidth);
		points[6] = calculateLightSpaceFrustumCorner(nearBottom, rightVector, nearWidth);
		points[7] = calculateLightSpaceFrustumCorner(nearBottom, leftVector, nearWidth);
		return points;
	}

	/**
	 * Calculates one of the corner vertices of the view frustum in world space
	 * and converts it to light space.
	 * 
	 * @param startPoint
	 *            - the starting center point on the view frustum.
	 * @param direction
	 *            - the direction of the corner from the start point.
	 * @param width
	 *            - the distance of the corner from the start point.
	 * @return - The relevant corner vertex of the view frustum in light space.
	 */
	private Vec4f calculateLightSpaceFrustumCorner(Vec3f startPoint, Vec3f direction, float width)
	{
		Vec3f point = startPoint.addN(direction.getX() * width, direction.getY() * width, direction.getZ() * width);
		Vec4f point4f = new Vec4f(point.getX(), point.getY(), point.getZ(), 1f);
		Mat4.transform(lightViewMatrix, point4f, point4f);
		return point4f;
	}

	/**
	 * @return The rotation of the camera represented as a matrix.
	 */
	private Mat4 calculateCameraRotationMatrix()
	{
		Mat4 rotation = Mat4.identity();
		rotation.rotate(Quatf.getFromAxis(new Vec3f(0.0f, 1.0f, 0.0f), -cam.getTransform().getRotation().getEulerYaw()));
		rotation.rotate(Quatf.getFromAxis(new Vec3f(1.0f, 0.0f, 0.0f), -cam.getTransform().getRotation().getEulerPitch()));
		return rotation;
	}

	/**
	 * Calculates the width and height of the near and far planes of the
	 * camera's view frustum. However, this doesn't have to use the "actual" far
	 * plane of the view frustum. It can use a shortened view frustum if desired
	 * by bringing the far-plane closer, which would increase shadow resolution
	 * but means that distant objects wouldn't cast shadows.
	 */
	private void calculateWidthsAndHeights()
	{
		farWidth = (float) (SHADOW_DISTANCE * Math.tan(this.fov * Maths.DEG_TO_RAD));
		nearWidth = (float) (this.near_plane * Math.tan(this.fov * Maths.DEG_TO_RAD));
		farHeight = farWidth / getAspectRatio();
		nearHeight = nearWidth / getAspectRatio();
	}

	/**
	 * @return The aspect ratio of the display (width:height ratio).
	 */
	private float getAspectRatio()
	{
		Window w = EngineRegistry.getCurrentWindow();
		return (float) w.getWidth() / (float) w.getHeight();
	}

}
