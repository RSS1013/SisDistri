import React from 'react';
import { AlertCircle, CheckCircle, Info, XCircle, X } from 'lucide-react';
import { useAlert } from '../context/AlertContext';

export const AlertContainer: React.FC = () => {
  const { alerts, removeAlert } = useAlert();
  
  if (alerts.length === 0) return null;
  
  return (
    <div className="fixed bottom-4 right-4 z-50 flex flex-col gap-2 max-w-md w-full">
      {alerts.map(alert => (
        <div
          key={alert.id}
          className={`card glass group p-4 shadow-lg animate-slide-in flex items-start gap-3 ${
            alert.type === 'success' ? 'border-success/20 text-success-700 dark:text-success-300' :
            alert.type === 'error' ? 'border-error/20 text-error-700 dark:text-error-300' :
            alert.type === 'warning' ? 'border-warning/20 text-warning-700 dark:text-warning-300' :
            'border-secondary/20 text-secondary-700 dark:text-secondary-300'
          }`}
        >
          <div className="flex-shrink-0">
            {alert.type === 'success' && <CheckCircle className="h-5 w-5" />}
            {alert.type === 'error' && <XCircle className="h-5 w-5" />}
            {alert.type === 'warning' && <AlertCircle className="h-5 w-5" />}
            {alert.type === 'info' && <Info className="h-5 w-5" />}
          </div>
          <div className="flex-1 text-sm">{alert.message}</div>
          <button
            onClick={() => removeAlert(alert.id)}
            className="flex-shrink-0 h-5 w-5 rounded-full inline-flex items-center justify-center opacity-60 hover:opacity-100"
            aria-label="Close alert"
          >
            <X className="h-4 w-4" />
          </button>
        </div>
      ))}
    </div>
  );
};