import { WebPlugin } from '@capacitor/core';

import type { CameraPluginOptions, GreatDayCameraPlugin } from './definitions';

export class GreatDayCameraWeb extends WebPlugin implements GreatDayCameraPlugin {
  async getCameraSwap(_options?: CameraPluginOptions | undefined): Promise<any> {
    throw new Error('Method not implemented.');
  }
  async getCamera(_options?: CameraPluginOptions | undefined): Promise<any> {
    throw new Error('Method not implemented.');
  }
}
