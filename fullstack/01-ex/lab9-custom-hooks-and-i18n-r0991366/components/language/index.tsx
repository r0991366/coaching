"use client";

import { useRouter, usePathname, useParams } from "next/navigation";
import { ChangeEvent, useTransition } from "react";

const SUPPORTED_LOCALES = ["en", "es"] as const;

const Language: React.FC = () => {
  const router = useRouter();
  const pathname = usePathname();
  const params = useParams();
  const [isPending, startTransition] = useTransition();

  const currentLocale = params?.locale
    ? Array.isArray(params.locale)
      ? params.locale[0]
      : params.locale
    : "en";

  const handleLanguageChange = (event: ChangeEvent<HTMLSelectElement>) => {
    const newLocale = event.target.value;
    if (!SUPPORTED_LOCALES.includes(newLocale as any)) return;

      // Split pathname
      const segments = pathname.split("/").filter(Boolean);

      // Replace the locale
      segments[0] = newLocale;

      // Build the new path
      const newPath = "/" + segments.join("/");

      startTransition(() => {
        router.push(newPath);
      });
  };

  return (
    <div className="ml-6">
      <label htmlFor="language" className="text-white">
        Language
      </label>
      <select
        id="language"
        className="ml-2 p-1 text-black"
        value={currentLocale}
        onChange={handleLanguageChange}
        disabled={isPending}
      >
        <option value="en">English</option>
        <option value="es">Español</option>
      </select>
    </div>
  );
};

export default Language;