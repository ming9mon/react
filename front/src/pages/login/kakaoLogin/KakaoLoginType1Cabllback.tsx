import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";

const KakaoLoginType1Cabllback: React.FC = () => {
    const location = useLocation()
    const [userInfo, setUserInfo] = useState<any>(null);

    useEffect(() => {
        const code = new URLSearchParams(location.search).get('code');
        console.log(code)
        if (code) {
            const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY;
            const REDIRECT_URI =import.meta.env.VITE_KAKAO_REDIRECT_URI_TYPE1;
            const URL = import.meta.env.VITE_KAKAO_OAUTH_URL;

            fetch(`https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=YOUR_REST_API_KEY&redirect_uri=YOUR_REDIRECT_URI&code=${code}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    const { access_token } = data;

                    fetchUserInfo(access_token)
                    // // 사용자 정보 요청
                    // fetch('https://kapi.kakao.com/v2/user/me', {
                    //     method: 'GET',
                    //     headers: {
                    //         'Authorization': `Bearer ${access_token}`,
                    //     },
                    // })
                    //     .then((response) => response.json())
                    //     .then((userData) => {
                    //         setUserInfo(userData);
                    //     })
                    //     .catch((error) => {
                    //         console.error('사용자 정보 요청 오류:', error);
                    //     });
                })
                .catch((error) => {
                    console.error('액세스 토큰 요청 오류:', error);
                });
        }
    }, [location]);

    const fetchUserInfo = (accessToken: string) => {
        window.Kakao.API.request({
            url: '/v2/user/me',
            success: (res: any) => {
                const kakaoAccount = res.kakao_account;
                console.log('사용자 정보:', kakaoAccount);
                // 여기서 사용자 정보를 상태로 관리하거나 백엔드로 전송할 수 있습니다.
            },
            fail: (err: any) => {
                console.error('사용자 정보 요청 실패:', err);
            },
        });
    };

    return <div>카카오 로그인 처리 중...</div>;
}

export default KakaoLoginType1Cabllback;