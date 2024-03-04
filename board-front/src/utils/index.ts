export const convertUrlToFile = async (url: string ) => {
    
    // 특정한 URL을 받아와서, 그 URL을 실제 파일로 변경해주는,
    // 타입스크립트에서 쓸 수 있는 파일 객체로 바꿔주는 작업.
    
    const response = await fetch(url);
    const data = await response.blob();
    const extend = url.split('.').pop();
    const fileName = url.split('/').pop();
    const meta = { type: `image/${extend}`};

    return new File([data], fileName as string, meta);
};

export const convertUrlsToFile = async (urls: string[]) => {
    const files: File[] = [];
    for (const url of urls) {
        const file = await convertUrlToFile(url);
        files.push(file);
    }
    return files;
}   