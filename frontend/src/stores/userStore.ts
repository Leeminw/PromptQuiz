import {create} from 'zustand';
import { persist } from "zustand/middleware";

interface UserState {
    user: User | null;
    setUser: (user: User) => void;
    clearUser: () => void;
}

const useUserStore = create(
    persist<UserState>(

    (set,get) => ({
        user: null,
        setUser: (user: User) => set({ user: user }),
        clearUser: () => set({ user: null }),
        })
        ,{
            name:"user"
        }
    )
);

export default useUserStore;
