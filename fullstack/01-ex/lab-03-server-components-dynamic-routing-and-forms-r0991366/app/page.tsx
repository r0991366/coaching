import Image from 'next/image';
import styles from '@styles/home.module.css';
import { useTranslations } from 'next-intl';
import coursesLogo from '@assets/images/courses.png';

const Home = () => {
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
        <h1 className="pl-6 text-4xl text-gray-800">Welcome!</h1>
      </span>

      <div className="mt-6 w-1/3 mx-auto text-center">
        <p>
          Courses lets you see as a lecturer all the courses you are teaching
          and as a student all the courses you are enrolled in. You can also see
          when the courses are scheduled and the students enrolled in each
          course.
        </p>
      </div>
    </>
  );
};

export default Home;
