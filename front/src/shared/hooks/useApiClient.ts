import { usePathname } from "next/navigation";
import { ApiConfig, ApiResponse, API_CONFIG_KEYS } from "../types/api";
import * as apiClient from "../api/apiClient";
import { AxiosResponse } from "axios";

export const useApiClient = <T = any>() => {
    const pahtName = usePathname();

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
            basePath = pahtName
        }
        
        
        if (apiConfig?.prefixPath) {
            return joinUrl(apiConfig.prefixPath, basePath);
        }
        
        if (apiConfig?.subfixPath) {
            return joinUrl(basePath, apiConfig.subfixPath);
        }

        return basePath;
    }

    const isApiConfig = (param: any): param is ApiConfig => {
        return param !== null &&
            typeof param === "object" &&
            API_CONFIG_KEYS.some((key) => key in param);
    }

    const getApiConfig = (param?: ApiConfig): ApiConfig => {
        if (isApiConfig(param)) {
            return param;
        } else {
            return { body: param }
        }
    }

    const search = async (param?: ApiConfig): Promise<ApiResponse<T>> => {
        const apiConfig = getApiConfig(param);
        const queryString = toQueryString(apiConfig?.body)
        const baseUrl = getApiUrl(apiConfig)

        const url = queryString ? `${baseUrl}?${queryString}` : baseUrl

        return await apiClient.get(url);
    }

    const save = async (param?: ApiConfig): Promise<ApiResponse<T>> => {
        return await post(param);
    }

    const post = async (param?: ApiConfig): Promise<ApiResponse<T>> => {
        const apiConfig = getApiConfig(param);
        return await apiClient.post(getApiUrl(apiConfig), apiConfig?.body);
    }

    const del = async (param?: ApiConfig): Promise<ApiResponse<T>> => {
        const apiConfig = getApiConfig(param);
        return await apiClient.del(getApiUrl(apiConfig));
    }

    const download = async (param?: ApiConfig): Promise<AxiosResponse<Blob>> => {
        const apiConfig = getApiConfig(param);
        return await apiClient.download(getApiUrl(apiConfig), apiConfig?.body);
    }

    return { search, save, post, del, download };
}