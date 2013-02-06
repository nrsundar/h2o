import os, json, unittest, time, shutil, sys
sys.path.extend(['.','..','py'])

import h2o, h2o_cmd

class Basic(unittest.TestCase):
    def tearDown(self):
        h2o.check_sandbox_for_errors()

    @classmethod
    def setUpClass(cls):
        h2o.build_cloud(node_count=1)

    @classmethod
    def tearDownClass(cls):
        h2o.tear_down_cloud()

    def test_B_hhp_107_01_loop(self):
        timeoutSecs = 10
        trial = 1
        n = h2o.nodes[0]
        for x in xrange (1,30,1):
            sys.stdout.write('.')
            sys.stdout.flush()

            csvPathname = h2o.find_file("smalldata/hhp_107_01.data.gz")
            put = n.put_file(csvPathname)
            parseKey = n.parse(put['key'])

            ### print 'Trial:', trial
            trial += 1

if __name__ == '__main__':
    h2o.unit_main()
