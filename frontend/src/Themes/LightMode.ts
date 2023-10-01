import { grey } from "@mui/material/colors";
import { createTheme } from "@mui/material";

export const LightMode = createTheme({
  shape: {
    borderRadius: 10,
  },
  palette: {
    mode: "light",
    primary: {
      main: "#f2f2f2",
      dark: "#c9c5c5",
    },
    background: {
      default: "#b7b6b6",
      paper: "#f2f2f2",
    },
  },
  typography: {
    body1: {
      color: "#404040",
      fontSize: "18px",
    },
    body2: {
      color: "#404040",
      fontSize: "16px",
    },
    h1: {
      color: "#404040",
      fontSize: 20,
    },
  },
  components: {
    MuiPaper: {
      defaultProps: {
        elevation: 3,
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          "& label.Mui-focused": {
            color: "black",
          },
          "& .MuiInput-underline:after": {
            borderBottomColor: "black",
          },
          "& .MuiFilledInput-underline:after": {
            borderBottomColor: "black",
          },
          "& .MuiOutlinedInput-root": {
            "&.Mui-focused fieldset": {
              borderColor: "black",
            },
          },
        },
      },
    },
    // @ts-ignore - this isn't in the TS because DataGird is not exported from `@mui/material`
    MuiDataGrid: {
      styleOverrides: {
        row: {
          "&.Mui-selected": {
            backgroundColor: "#c9c5c5",
          },
        },
      },
    },
  },
});
