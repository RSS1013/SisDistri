import React, { createContext, useContext, useState, useCallback } from 'react';

type AlertType = 'success' | 'error' | 'warning' | 'info';

interface Alert {
  id: string;
  type: AlertType;
  message: string;
}

interface AlertContextType {
  alerts: Alert[];
  addAlert: (type: AlertType, message: string) => void;
  removeAlert: (id: string) => void;
}

const AlertContext = createContext<AlertContextType | undefined>(undefined);

export const AlertProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [alerts, setAlerts] = useState<Alert[]>([]);
  
  const addAlert = useCallback((type: AlertType, message: string) => {
    const id = Math.random().toString(36).substring(2, 9);
    setAlerts(prev => [...prev, { id, type, message }]);
    
    // Auto-remove after 5 seconds
    setTimeout(() => {
      removeAlert(id);
    }, 5000);
  }, []);
  
  const removeAlert = useCallback((id: string) => {
    setAlerts(prev => prev.filter(alert => alert.id !== id));
  }, []);
  
  return (
    <AlertContext.Provider value={{ alerts, addAlert, removeAlert }}>
      {children}
    </AlertContext.Provider>
  );
};

export const useAlert = (): AlertContextType => {
  const context = useContext(AlertContext);
  
  if (context === undefined) {
    throw new Error('useAlert must be used within an AlertProvider');
  }
  
  return context;
};