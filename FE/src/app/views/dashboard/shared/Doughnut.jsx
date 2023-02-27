import React from 'react';
import { useTheme } from '@mui/system';
import ReactEcharts from 'echarts-for-react';
import { useState, useEffect } from 'react';

const DoughnutChart = ({ height, color = [], data = [] }) => {
  const theme = useTheme();

  const [list, setList] = useState([]);

  useEffect(() => {
    let newList = [];
    data.map((item) => {
      newList.push({
        ...item,
        value: item?.count,
      });
    });
    setList(newList);
  }, [data]);

  const option = {
    legend: {
      orient: 'vertical',
      left: 'left',
      //   show: true,
      //   itemGap: 20,
      //   icon: 'circle',
      top: 50,
      textStyle: {
        color: theme.palette.text.secondary,
        fontSize: 13,
        fontFamily: 'roboto',
      },
    },
    tooltip: {
      show: true,
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)',
    },
    xAxis: [
      {
        axisLine: {
          show: false,
        },
        splitLine: {
          show: false,
        },
      },
    ],
    yAxis: [
      {
        axisLine: {
          show: false,
        },
        splitLine: {
          show: false,
        },
      },
    ],

    series: [
      {
        name: '',
        type: 'pie',
        radius: '55%',
        center: ['60%', '50%'],
        // avoidLabelOverlap: false,
        // hoverOffset: 5,
        // stillShowZeroSum: false,
        label: {
          //   normal: {
          //     show: true,
          //     position: 'center', // shows the description data to center, turn off to show in right side
          //     textStyle: {
          //       color: theme.palette.text.secondary,
          //       fontSize: 13,
          //       fontFamily: 'roboto',
          //     },
          //     formatter: '{a}',
          //   },
          emphasis: {
            show: true,
            textStyle: {
              fontSize: '14',
              fontWeight: 'normal',
              // color: "rgba(15, 21, 77, 1)"
            },
            formatter: '{b} \n{c} ({d}%)',
          },
        },
        labelLine: {
          normal: {
            show: true,
          },
        },
        data: list,
        // [
        //     {
        //       value: 65,
        //       name: 'Phòng Nghiên cứu và Phát triển sản phẩm',
        //     },
        //     {
        //       value: 20,
        //       name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq3',
        //     },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq' },
        //     { value: 0, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq1' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq2' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq33' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq4' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq5' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq6' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq7' },
        //     { value: 15, name: 'OPhòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq' },
        //     { value: 15, name: 'Phòng qưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeqưeq9' },
        // ],
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  };

  return (
    <ReactEcharts
      style={{ height: height }}
      option={{
        ...option,
        color: [...color],
      }}
    />
  );
};

export default DoughnutChart;
