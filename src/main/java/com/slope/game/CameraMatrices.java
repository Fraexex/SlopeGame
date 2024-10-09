package com.slope.game;

import org.joml.*;
import org.joml.Math;

import java.nio.ByteBuffer;

public class CameraMatrices {
    public static final int SIZE =
            Float.BYTES * 16 + // The size of our projection matrix.
            Float.BYTES * 16 + // The size of our view matrix.
            Float.BYTES * 9 + // The size of our rotation matrix.
            Float.BYTES * 3 + // The size of our camera position.
            Float.BYTES * 2; // The size of our near and far plane.

    // Our data sent to the GPU.
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix3f rotationMatrix;
    private final Vector3f position;
    private float nearPlane;
    private float farPlane;

    // Our data that stays.
    private float verticalAngle;

    public CameraMatrices() {
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.rotationMatrix = new Matrix3f();
        this.position = new Vector3f();
        this.nearPlane = 0.0f;
        this.farPlane = 0.0f;

        this.verticalAngle = 0.0f;
    }

    public void write(ByteBuffer buffer) {
        this.projectionMatrix.get(0, buffer);
        this.viewMatrix.get(Float.BYTES * 16, buffer);
        this.rotationMatrix.get(Float.BYTES * 32, buffer);
        this.position.get(Float.BYTES * 41, buffer);
        buffer.putFloat(Float.BYTES * 44, this.nearPlane);
        buffer.putFloat(Float.BYTES * 45, this.farPlane);
    }

    // TODO (Gain one point): Have a update method here that calculates our matrix.
    // @param projection (datatype: Matrix4fc) -> The projection of the camera.
    // @param modelView (datatype: Matrix4fc) -> The modelview rotation of the camera.
    // @param position (datatype: Vector3fc) -> The position of the camera.
    // @param zFar (datatype: float) -> The far clipping plane of the camera.
    // @param zNear (datatype: float) -> The near clipping plane of the camera.
    public void update(Matrix4fc projection, Matrix4fc modelView, Vector3fc pos, float zNear, float zFar) {
        updateRotationMat();
    }

    public void updateRotationMat() {
        float cos_y = Math.acos(verticalAngle);
        float sin_y = Math.asin(verticalAngle);

        rotationMatrix.m11(cos_y);
        rotationMatrix.m12(-sin_y);
        rotationMatrix.m21(sin_y);
        rotationMatrix.m22(cos_y);
    }
}
