declare module '@capacitor/core' {
  interface PluginRegistry {
    VKAuth: VKAuthPlugin;
  }
}

export interface VKAuthPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
