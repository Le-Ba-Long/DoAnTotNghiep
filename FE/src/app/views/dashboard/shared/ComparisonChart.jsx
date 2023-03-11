import { useTheme } from '@mui/system';
import ReactEcharts from 'echarts-for-react';
import { useState, useEffect } from 'react';

const ComparisonChart = ({ height, color = [], data = {} }) => {
  const theme = useTheme();
  const [list, setList] = useState([]);

  useEffect(() => {
    let newList = [];
    data?.value?.map((item, index) => {
      console.log(item);
      newList.push([`T${index + 1}`, item]);
    });
    setList(newList);
  }, [data]);

  const option = {
    grid: { top: '10%', bottom: '10%', right: '5%' },
    legend: { show: false },
    color: ['rgba(34, 51, 136, 0.8)'],
    barGap: 0,
    barMaxWidth: '64px',
    dataset: {
      source: list,
      // [
      //   // ['Month', 'Website', 'App'],
      //   ['Jan', 2200],
      //   ['Feb', 800],
      //   ['Mar', 700],
      //   ['Apr', 1500],
      //   ['May', 2450],
      //   ['June', 1700],
      // ],
    },
    xAxis: {
      type: 'category',
      axisLine: { show: false },
      splitLine: { show: false },
      axisTick: { show: false },
      axisLabel: {
        fontSize: 13,
        fontFamily: 'roboto',
        color: theme.palette.text.secondary,
      },
    },
    yAxis: {
      axisLine: { show: false },
      axisTick: { show: false },
      splitLine: {
        lineStyle: { color: theme.palette.text.secondary, opacity: 0.15 },
      },
      axisLabel: {
        fontSize: 13,
        fontFamily: 'roboto',
        color: theme.palette.text.secondary,
      },
    },
    // Declare several bar series, each will be mapped
    // to a column of dataset.source by default.
    series: [{ type: 'bar' }],
  };

  return <ReactEcharts style={{ height: height }} option={{ ...option }} />;
};

export default ComparisonChart;
