import Header from '@components/header';
import '@styles/globals.css';
import Providers from './Providers';

const RootLayout = async ({ children }: { children: React.ReactNode }) => {
  return (
    <html lang="en">
      <body>
        <Providers>
          <Header />
          <main className="p-6 min-h-screen flex flex-col items-center">
            {children}
          </main>
        </Providers>
        
      </body>
    </html>
  );
};

export default RootLayout;
