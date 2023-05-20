import axios from "axios";

export interface CatServiceHooks {
    getRandomCatImage: () => Promise<any>;
}

export const useCatAxiosInstance = (): CatServiceHooks => {
    const catAxiosInstance = axios.create({
        baseURL: "https://cataas.com",
        timeout: 5000,
        headers: {}
    });

    const getRandomCatImage = async (): Promise<any> => {
        return await catAxiosInstance.get("/cat", { responseType: 'blob' })
            .then((response) => {
                return response.data;
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return { getRandomCatImage };
};

