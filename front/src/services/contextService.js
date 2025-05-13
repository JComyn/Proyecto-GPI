export const setContext = (userId, email) => {
    localStorage.setItem("userId", userId);
    localStorage.setItem("email", email);
};

export const removeContext = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("email");
}

export const getUserId = () => {
    localStorage.getItem("userId");
};

export const getEmail = () => {
    localStorage.setItem("email", email);
};