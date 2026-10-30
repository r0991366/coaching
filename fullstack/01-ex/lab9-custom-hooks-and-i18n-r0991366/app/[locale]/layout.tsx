import Header from '@components/header';
import { AuthProvider } from '@context/AuthContext';
import '@styles/globals.css';
import { NextIntlClientProvider } from 'next-intl';
import { getMessages } from 'next-intl/server';

const RootLayout = async ({ 
  children, 
  params, 
}: { 
  children: React.ReactNode;
  params:Promise<{ locale: string }> 
}) => {
  const { locale } = await params;
  const translations = await getMessages();

  return (
    <html lang="en">
      <body>
        <NextIntlClientProvider locale={locale} messages={translations}>
          <AuthProvider>
            <Header />
            <main className="p-6 min-h-screen flex flex-col items-center">
              {children}
            </main>
          </AuthProvider>
        </NextIntlClientProvider>
      </body>
    </html>
  );
};

export default RootLayout;
