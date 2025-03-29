import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import axios from 'axios';

const NaverLoginType2Callback = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const code = searchParams.get("code");

        if (code) {
            const sendCodeToBackend = async () => {
                try {
                    axios
                        .post('/api/login/naver', { code })
                        .then((response) => {
                            // 로그인 성공 시 처리 로직
                            //navigate('/'); // 홈 페이지로 리디렉션
                            console.log(response)
                        })
                        .catch((error: any) => {
                            console.log(error)
                            setError('로그인 처리 중 오류가 발생하였습니다.');
                        })
                        .finally(() => {
                            setLoading(false);
                        });
                } catch (error) {
                    console.error("네이버 로그인 실패", error);
                }
            };

            sendCodeToBackend();
        }
    }, [searchParams, navigate]);

    if (loading) {
        return <div>로그인 처리 중...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }
};

export default NaverLoginType2Callback;