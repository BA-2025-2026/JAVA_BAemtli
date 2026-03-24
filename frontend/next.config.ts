import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    output: 'export', // Erzeugt einen /out Ordner mit statischen Dateien
    images: {
        unoptimized: true, // Notwendig für statischen Export
    },
};

export default nextConfig;
