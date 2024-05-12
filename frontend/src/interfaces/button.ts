interface ActivateButton {
  [key: number]: boolean;
}
interface DisableButton {
  [key: number]: boolean;
}
interface ButtonProps {
  className?: string;
  children?: React.ReactNode;
  btnCurrentActivate: boolean;
  onClick?: () => void;
}
