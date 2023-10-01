import { Cloud, ContentCopy, ContentCut, ContentPaste } from "@mui/icons-material";
import { Divider, ListItemIcon, ListItemText, MenuItem, MenuList, Paper, Popover, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

interface PopOverMenuProps {
  anchorElement: any;
  open: boolean;
  onClose: { (): void };
  editStudent: React.Dispatch<React.SetStateAction<boolean>>;
  editStudentsCourses: React.Dispatch<React.SetStateAction<boolean>>;
  deleteStudent: React.Dispatch<React.SetStateAction<boolean>>;
}

export const PopOverMenu = (props: PopOverMenuProps) => {
  return (
    <Popover
      open={props.open}
      anchorEl={props.anchorElement}
      onClose={props.onClose}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "left",
      }}
      transformOrigin={{
        vertical: "top",
        horizontal: "right",
      }}
    >
      <Paper sx={{ width: 260, maxWidth: "100%" }}>
        <MenuList>
          <MenuItem
            onClick={() => {
              props.editStudent(true);
              props.onClose();
            }}
          >
            <ListItemIcon>
              <EditIcon fontSize='small' />
            </ListItemIcon>
            <ListItemText>Edit student</ListItemText>
          </MenuItem>
          <MenuItem
            onClick={() => {
              props.onClose();
            }}
          >
            <ListItemIcon>
              <EditIcon fontSize='small' />
            </ListItemIcon>
            <ListItemText>Edit courses of student</ListItemText>
          </MenuItem>
          <MenuItem
            onClick={() => {
              props.onClose();
              props.deleteStudent(true);
            }}
          >
            <ListItemIcon>
              <DeleteIcon fontSize='small' />
            </ListItemIcon>
            <ListItemText>Delete student</ListItemText>
          </MenuItem>
        </MenuList>
      </Paper>
    </Popover>
  );
};
