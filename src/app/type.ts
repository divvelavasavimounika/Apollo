export type User =
    {
        id: string;
        userName: string;
        email: string;
    }
export type Query =
    {
        getUsers: User[];

    }
export type Mutation1 =
    {
        addUser(userName: string, email: string): User
        deleteUserById(id: string): User
        updateUser(id: string, userName: string): User

    }

