import { Box, Grid, IconButton, Switch, Toolbar, Typography, useTheme } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import { Link } from "react-router-dom";
import HomeIcon from "@mui/icons-material/Home";
import { useState } from "react";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";

interface NavbarProps {
  lightMode: boolean;
  setLightMode: React.Dispatch<React.SetStateAction<boolean>>;
}

const Navbar = (props: NavbarProps) => {
  const [checked, setChecked] = useState<boolean>(true);
  const [showHome, setShowHome] = useState<boolean>(true);

  const theme = useTheme();

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(event.target.checked);
    props.setLightMode(!props.lightMode);
  };

  return (
    <Grid container item xs={12} py={2} px={2}>
      <AppBar enableColorOnDark position='static' sx={{ borderRadius: "10px" }}>
        <Toolbar>
          <Grid container item direction={"row"} alignItems={"center"}>
            <Grid container xs={11} item alignItems={"center"}>
              <Grid item minWidth={"50px"} justifyContent='center'>
                <IconButton
                  edge='start'
                  color='inherit'
                  aria-label='menu'
                  sx={{ mr: 2 }}
                  onMouseEnter={() => setShowHome(false)}
                  onMouseLeave={() => setShowHome(true)}
                >
                  <Link style={{ textDecoration: "none" }} to='/'>
                    {showHome ? (
                      <HomeIcon sx={{ color: theme.typography.body1.color }} />
                    ) : (
                      <img src={require("../Assets/logo.png")} height='25px' width='25px' />
                    )}
                  </Link>
                </IconButton>
              </Grid>
              <Grid item>
                <Typography variant='h6' mr={3}>
                  <Link style={{ textDecoration: "none" }} to='/students'>
                    <Typography variant='h1'>Students</Typography>
                  </Link>
                </Typography>
              </Grid>
              <Grid item>
                <Typography variant='h6'>
                  <Link style={{ textDecoration: "none" }} to='/courses'>
                    <Typography variant='h1'>Courses</Typography>
                  </Link>
                </Typography>
              </Grid>
            </Grid>
            <Grid container item xs={1} direction={"row"} alignItems={"center"} justifyContent={"flex-end"}>
              {props.lightMode ? <LightModeIcon /> : <DarkModeIcon />}
              <Switch
                color='default'
                checked={checked}
                onChange={handleChange}
                inputProps={{ "aria-label": "controlled" }}
              />
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    </Grid>
  );
};

export default Navbar;
