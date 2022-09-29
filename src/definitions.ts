export interface GreatDayCameraPlugin {
  getCamera(options?: CameraPluginOptions): Promise<any>;
  getCameraSwap(options?: CameraPluginOptions): Promise<any>;
}

export interface CameraPluginOptions {
  photoName: string;
  quality: number;
  maxSize: number;
}