import UserLoginForm from "@components/users/UserLoginForm";

const LoginPage = () => {
    return (
    <>
        <h1 className="text-center">Login</h1>
        <section className="flex flex-col justify-center">
            <UserLoginForm />
        </section>
    </>
    );
};
export default LoginPage;