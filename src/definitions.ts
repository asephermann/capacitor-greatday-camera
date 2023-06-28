export interface GreatDayCameraPlugin {
  getCamera(options?: CameraPluginOptions): Promise<{result: string}>;
  getCameraSwap(options?: CameraPluginOptions): Promise<{result: string}>;
}

export interface CameraPluginOptions {
  photoName: string;
  quality: string;
  maxSize: string;
  isFacingBack?: boolean;
  showFaceArea?: boolean;
}