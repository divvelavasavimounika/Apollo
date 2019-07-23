interface Alert {
    type: string;
    message: string;
  }
  
 export const ALERTS: Alert[] = [{
      type: 'success',
      message: 'You successfully read this important alert message.',
    }, {
      type: 'info',
      message: 'This alert needs your attention, but its not super important.',
    }, {
      type: 'warning',
      message: 'Better check yourself, you are not looking too good.',
    }, {
      type: 'danger',
      message: 'Change a few things up and try submitting again.',
    }
  ];