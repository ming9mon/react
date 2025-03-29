import {createProxyMiddleware, Options} from 'http-proxy-middleware';

const proxyConfig: Options = {
    target: 'http://localhost:8080', // 백엔드 서버의 주소
    changeOrigin: true,
    pathRewrite: {
        '^/api': '', // '/api'를 제거하여 백엔드 서버의 '/login/naver' 경로로 요청을 전달
    },
};

export default function (app: any): void {
    app.use('/api', createProxyMiddleware(proxyConfig));
}