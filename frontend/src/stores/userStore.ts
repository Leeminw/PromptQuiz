import {create} from 'zustand';
import { persist } from "zustand/middleware";



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
