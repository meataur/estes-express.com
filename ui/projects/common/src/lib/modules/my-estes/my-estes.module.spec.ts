import { MyEstesModule } from './my-estes.module';

describe('MyEstesModule', () => {
  let myEstesModule: MyEstesModule;

  beforeEach(() => {
    myEstesModule = new MyEstesModule();
  });

  it('should create an instance', () => {
    expect(myEstesModule).toBeTruthy();
  });
});
