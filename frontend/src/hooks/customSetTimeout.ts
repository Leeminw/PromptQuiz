import { useEffect, useRef } from 'react';

const customSetTimeout = (firstCallback: () => void, nextCallback: () => void, delay: number, dependencies: any[]) => {
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);
  useEffect(() => {
    if (timeoutRef.current !== null) {
      clearTimeout(timeoutRef.current);
    }
    firstCallback();
    timeoutRef.current = setTimeout(() => {
      nextCallback();
    }, delay);
    return () => {
      if (timeoutRef.current !== null) {
        clearTimeout(timeoutRef.current);
      }
    };
  }, [...dependencies]);
};

export default customSetTimeout;
