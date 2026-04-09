import { usePathname } from "next/navigation";
import { ApiConfig } from "../types/api";
import * as apiClient from "../api/apiClient";

export const useApiClient = () => {

    const toQueryString = (params?: Record<string, unknown>) => {
        if (!params) return ''

        const query = new URLSearchParams()

        Object.entries(params).forEach(([key, value]) => {
            if (value !== undefined && value !== null) {
            query.append(key, String(value))
            }
        })

        return query.toString()
    }

    const joinUrl = (...parts: Array<string | undefined>) => {
        return parts
            .filter((part): part is string => !!part)
            .map((part, index) => {
            if (index === 0) {
                return part.replace(/\/+$/, '')
            }
            return part.replace(/^\/+|\/+$/g, '')
            })
            .join('/')
    }

    const getApiUrl = (apiConfig?: ApiConfig) => {
        let basePath;

        if (apiConfig?.url) {
            basePath = apiConfig.url;
        }  else {
            basePath = usePathname()
        }
        
        if (apiConfig?.subUrl) {
            return joinUrl(basePath, apiConfig.subUrl);
        }

        return basePath;
    }

    const get = async (apiConfig?: ApiConfig) => {
        const queryString = toQueryString(apiConfig?.body)

        const url = queryString ? `${getApiUrl()}?${queryString}` 
        : getApiUrl()

        return await apiClient.get(url);
    }

    const post = async (apiConfig?: ApiConfig) => {
        return await apiClient.post(getApiUrl(), apiConfig?.body);
    }

    const download = async (apiConfig?: ApiConfig) => {
        return await apiClient.download(getApiUrl(apiConfig), apiConfig?.body);
    }

    return { get, post, download };
}