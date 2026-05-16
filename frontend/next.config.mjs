/** @type {import('next').NextConfig} */
const nextConfig = {
  /* config options here */
  output: "standalone", // optimize build file for container app (smaller file size)
  images: {
    unoptimized: true, // Necessary for static export?
  },
};

export default nextConfig;
