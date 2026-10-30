import Image from 'next/image';
import styles from '@styles/home.module.css';
import coursesLogo from '@assets/images/courses.png';
import { getTranslations } from 'next-intl/server';

const Home = async () => {
    const t = await getTranslations('home');

  return (
    <>
      <span className="flex flex-row justify-center items-center">
        <Image
          src={coursesLogo}
          alt="Courses Logo"
          className={styles.vercelLogo}
          width={50}
          height={50}
          priority
        />
        <h1 className="pl-6 text-4xl text-gray-800">Courses App</h1>
      </span>

      <div className="mt-6 w-1/3 mx-auto text-center">
        <p>
          {t('description')}
        </p>
      </div>
    </>
  );
};

export default Home;
