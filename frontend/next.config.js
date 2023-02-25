/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ["tong.visitkorea.or.kr"],
  },
  experimental: {
    appDir: true,
  },
};
module.exports = nextConfig;
