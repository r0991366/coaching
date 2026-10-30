import '@styles/globals.css';
import Header from '@components/header';

const RootLayout = async ({ children }: { children: React.ReactNode }) => {
  return (
    <html lang="en">
      <body>
        <Header />
        <main className="p-6 min-h-screen flex flex-col items-center">
          {children}
        </main>
      </body>
    </html>
  );
};

export default RootLayout;
