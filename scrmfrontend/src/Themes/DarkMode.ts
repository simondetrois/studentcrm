import { createTheme } from "@mui/material";

export const DarkMode = createTheme({
  shape: {
    borderRadius: 10,
  },
  palette: {
    mode: "dark",
    primary: {
      main: "#333333",
      dark: "#737070",
    },

    background: {
      default: "#333333",
      paper: "#3b3b3b",
    },
  },

  typography: {
    body1: {
      color: "#e0dbdb",
      fontSize: "18px",
    },
    body2: {
      color: "#e0dbdb",
      fontSize: "16px",
    },
    h1: {
      color: "#e0dbdb",
      fontSize: 20,
    },
  },

  components: {
    MuiPaper: {
      defaultProps: {
        color: "#fff",
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          "& label.Mui-focused": {
            color: "#e0dbdb",
          },
          "& .MuiInput-underline:after": {
            borderBottomColor: "#e0dbdb",
          },
          "& .MuiFilledInput-underline:after": {
            borderBottomColor: "#e0dbdb",
          },
          "& .MuiOutlinedInput-root": {
            "&.Mui-focused fieldset": {
              borderColor: "#e0dbdb",
            },
          },
        },
      },
    },
    //@ts-ignore - this isn't in the TS because DataGird is not exported from `@mui/material`
    MuiDataGrid: {
      styleOverrides: {
        row: {
          "&.Mui-selected": {
            backgroundColor: "#737070",
          },
        },
      },
    },
  },
});
