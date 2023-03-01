/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ["tong.visitkorea.or.kr"],
  },
  experimental: {
    appDir: true,
    typedRoutes: true,
  },
};
module.exports = nextConfig;
