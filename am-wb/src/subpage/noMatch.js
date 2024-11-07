import { Button, Result } from 'antd';
import React from 'react';
import history from '../store/history'

const NoFoundPage = () => (
  <Result
    status="404"
    title="404"
    subTitle="Sorry, the page you visited does not exist."
    extra={
      <Button type="primary" onClick={() => history.push('/index')}>
        Back Home
      </Button>
    }
  ></Result>
);

export default NoFoundPage;
