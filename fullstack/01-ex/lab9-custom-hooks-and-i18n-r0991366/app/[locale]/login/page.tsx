import UserLoginForm from "@components/users/UserLoginForm";
import { getTranslations } from "next-intl/server";

const LoginPage = async () => {

  return (
    <>
      <h1 className="text-center">Lecturers</h1>

      <section className="flex flex-col justify-center">
        <UserLoginForm />
      </section>
    </>
  );
};

export default LoginPage;
