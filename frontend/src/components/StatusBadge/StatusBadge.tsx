import type { ReactNode } from 'react';

type StatusBadgeProps = {
    status: string;
    className?: string;
};

const getStatusColor = (status: string) => {
    switch (status) {
        case 'ATIVO':
            return 'bg-blue-100 text-blue-800';
        case 'DEVOLVIDO':
            return 'bg-green-100 text-green-800';
        default:
            return 'bg-gray-100 text-gray-800';
    }
};

export const StatusBadge = ({ status, className = '' }: StatusBadgeProps) => (
    <span className={`px-2 py-1 rounded-full font-medium ${getStatusColor(status)} ${className}`}>
        {status}
    </span>
);
