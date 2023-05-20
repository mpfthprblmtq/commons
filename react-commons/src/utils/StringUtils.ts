export const capitalizeString = (str: string): string => {
    if (str) {
        return str[0].toUpperCase() + str.substring(1);
    }
    return str;
};