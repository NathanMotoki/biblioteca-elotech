import type { ReactNode } from 'react';

type PageContainerProps = {
    children: ReactNode;
    className?: string;
};

export const PageContainer = ({ children, className = '' }: PageContainerProps) => (
    <div className={`w-full min-h-screen pt-4 px-8 ${className}`}>
        {children}
    </div>
);

type PageTitleProps = {
    children: ReactNode;
    className?: string;
};

export const PageTitle = ({ children, className = '' }: PageTitleProps) => (
    <h1 className={`text-3xl font-bold mb-6 text-gray-800 ${className}`}>
        {children}
    </h1>
);
