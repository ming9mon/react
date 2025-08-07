import { createContext, useContext } from 'react';
import { DialogContextType, DialogOptions } from "@/shared/types/commonDialog";

export const DialogContext = createContext<DialogContextType>(null!);

export const useCommonDialog = (): ((opts: DialogOptions) => Promise<boolean>) => {
  const { showDialog } = useContext(DialogContext);
  return showDialog;
};