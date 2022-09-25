{
  outputs = { nixpkgs, ... }: with nixpkgs.legacyPackages.x86_64-linux; {
    devShell.x86_64-linux = mkShell {
      packages = [ maven chromedriver chromium openjdk ];
    };
  };
}
