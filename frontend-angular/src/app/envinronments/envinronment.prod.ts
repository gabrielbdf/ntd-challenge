

export const environment = {
    host: `${getCurrentHost()}:8083`
}

function getCurrentHost(): string {
    const hostname = window.location.hostname;
    return `http://${hostname}`;
}