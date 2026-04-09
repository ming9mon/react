import { useApiClient } from "../hooks/useApiClient"
import { ApiConfig } from "../types/api"

export const search = async (apiConfig?: ApiConfig) => {
    return await useApiClient().get(apiConfig);
}

export const save = async (apiConfig?: ApiConfig) => {
    return await useApiClient().post(apiConfig);
}

export const post = async (apiConfig?: ApiConfig) => {
    return await useApiClient().post(apiConfig);
}

export const download = async (apiConfig?: ApiConfig) => {
    return await useApiClient().download(apiConfig);
}