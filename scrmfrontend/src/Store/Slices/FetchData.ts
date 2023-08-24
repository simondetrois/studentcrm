import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Student } from "../../Data/Student";

interface FetchData {
  fetchData: boolean;
}

const initialState: FetchData = {
  fetchData: false,
};

export const fetchData = createSlice({
  name: "fetchData",
  initialState: initialState,
  reducers: {
    setFetchData: (state, action: PayloadAction<boolean>) => {
      state.fetchData = action.payload;
      return state;
    },
  },
});

export const { setFetchData: setFetchData } = fetchData.actions;
export default fetchData.reducer;
