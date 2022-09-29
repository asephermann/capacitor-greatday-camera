export interface GreatDayCameraPlugin {
  getCamera(options?: CameraPluginOptions): Promise<any>;
}

export interface CameraPluginOptions {
  photoName: string;
  disableFacingBack:boolean;
  quality: number;
  maxSize: number;
}