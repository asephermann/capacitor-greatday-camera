export interface GreatDayCameraPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
