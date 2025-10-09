import type { ReactNode } from 'react';

type TableProps = {
    children: ReactNode;
    className?: string;
};

export const Table = ({ children, className = '' }: TableProps) => (
    <div className={`w-full bg-white ${className}`}>
        <table className="w-full table-auto border-collapse">
            {children}
        </table>
    </div>
);

export const TableHead = ({ children }: { children: ReactNode }) => (
    <thead className="bg-gray-50 border-b border-gray-200">
        {children}
    </thead>
);

export const TableBody = ({ children }: { children: ReactNode }) => (
    <tbody className="bg-white divide-y divide-gray-100">
        {children}
    </tbody>
);

export const TableRow = ({ children, onClick, className = '' }: { children: ReactNode; onClick?: () => void; className?: string }) => (
    <tr className={`hover:bg-gray-50 cursor-pointer ${className}`} onClick={onClick}>
        {children}
    </tr>
);

export const TableHeader = ({ children }: { children: ReactNode }) => (
    <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 uppercase tracking-wider">
        {children}
    </th>
);

export const TableCell = ({ children }: { children: ReactNode }) => (
    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
        {children}
    </td>
);
