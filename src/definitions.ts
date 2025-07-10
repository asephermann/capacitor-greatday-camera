export interface GreatDayCameraPlugin {
  getCamera(options?: CameraPluginOptions): Promise<{result: string}>;
  getCameraSwap(options?: CameraPluginOptions): Promise<{result: string}>;
}

export interface CameraPluginOptions {
  cameraSource?: string;
  photoName: string;
  quality: string;
  maxSize: string;
  isFacingBack?: boolean;
  disablePreview?: boolean;
  showFaceArea?: boolean;
}